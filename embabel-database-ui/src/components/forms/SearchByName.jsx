import { useState } from "react";
import { Button,ControlGroup,InputGroup } from "@blueprintjs/core";
import { Search, Reset } from "@blueprintjs/icons";


function SearchByName({ onSearch, onReset }) {

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

    const handleReset = () => {
        if (onReset) {
            onReset();
        } //end if
    }

    return (
        <>
            <ControlGroup>
                <InputGroup type="text" placeholder="Search by name..." inputSize={50} onChange={(e) => setValue(e.target.value)} onKeyDown={handleKeyDown}/>
                <Button onClick={handleSearch}><Search/></Button>
                { (onReset) ? (<Button onClick={handleReset}><Reset/></Button>) : (<></>) }                                
            </ControlGroup>
        </>
    );
}

export default SearchByName