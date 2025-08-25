import { useState } from "react";
import { Button,ControlGroup,InputGroup } from "@blueprintjs/core";
import { Search } from "@blueprintjs/icons";


function SearchByName({ onSearch }) {

    const [value, setValue] = useState("");

    const handleSearch = () => {
        if (onSearch) {
            onSearch(value);
        }
    };

    const handleKeyDown = (e) => {
        if (e.key === "Enter") {
            handleSearch();
        }
    }

    return (
        <>
            <ControlGroup>
                <InputGroup type="text" placeholder="Search by name..." inputSize={50} onChange={(e) => setValue(e.target.value)} onKeyDown={handleKeyDown}/>
                <Button onClick={handleSearch}><Search/></Button>
            </ControlGroup>
        </>
    );
}

export default SearchByName