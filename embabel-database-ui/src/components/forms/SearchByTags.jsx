import { useEffect, useState } from "react";
import { Button,ControlGroup,MenuItem,Tooltip } from "@blueprintjs/core";
import { Search, Reset } from "@blueprintjs/icons";
import { MultiSelect } from "@blueprintjs/select";


function SearchByTags({ onSearch, onReset }) {
    const [tags, setTags] = useState([])
    const [selectedTags, setSelectedTags] = useState([]);

    const itemRenderer = (item, { handleClick, modifiers }) => {
        if (!modifiers.matchesPredicate) {
            return null;
        }
        return (
            <MenuItem
            active={modifiers.active}
            key={item.tag}
            onClick={handleClick}
            text={item.tag}
            />
        );
    };

    useEffect(() => {
        async function fetchTags() {
            try {
                const response = await fetch("/api/v1/tags");
                const data = await response.json();
                setTags(data);
            } catch (error) {
                console.error("Error loading tags:",error);
            }
        }

        fetchTags();
    }, []);

    const handleSearch = () => {
        if (onSearch) {
            onSearch(selectedTags);
        }
    };

    const handleReset = () => {
        if (onReset) {
            setSelectedTags([]);
            onReset();
        } //end if
    }    

    return (
        <>
            <ControlGroup>
                <Tooltip
                    content={<span>Choose a tag from the drop down list.  You can add multiple tags. Click on the button to then filter.</span>}
                    openOnTargetFocus={false}
                    placement="bottom"
                    usePortal={false}>
                    <MultiSelect items={tags} 
                        itemRenderer={itemRenderer} 
                        onItemSelect={item => setSelectedTags([...selectedTags, item])}
                        selectedItems={selectedTags}
                        tagRenderer={tags => tags.tag}/>
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

export default SearchByTags