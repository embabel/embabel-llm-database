import { useState } from "react";
import { Button,ControlGroup,InputGroup, Tooltip } from "@blueprintjs/core";
import { Search, Reset } from "@blueprintjs/icons";

function SearchByProviders({ onSearch, onReset }) {

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
                <Tooltip
                    content={<span>Enter a provider name to search by or just hit 'enter' to see all</span>}
                    openOnTargetFocus={false}
                    placement="bottom"
                    usePortal={false}>
                    <InputGroup type="text" placeholder="Search by provider..." inputSize={50} onChange={(e) => setValue(e.target.value)} onKeyDown={handleKeyDown}/>
                </Tooltip>
                <Button onClick={handleSearch}><Search/></Button>
                { (onReset) ? (
                    <Tooltip
                        content={<span>Click here to 'reset' your search and see all models.</span>}
                        openOnTargetFocus={false}
                        placement="right"
                        usePortal={false}>
                        <Button onClick={handleReset}><Reset/></Button>
                    </Tooltip>
                    ) : (<></>) }                                
            </ControlGroup>        
        </>
    );
}

export default SearchByProviders