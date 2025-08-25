import { useEffect, useState } from "react";
import { Button,ControlGroup,MenuItem } from "@blueprintjs/core";
import { Search } from "@blueprintjs/icons";
import { MultiSelect } from "@blueprintjs/select";


function SearchByTags() {
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

    return (
        <>
            <ControlGroup>
                <MultiSelect items={tags} 
                    itemRenderer={itemRenderer} 
                    onItemSelect={item => setSelectedTags([...selectedTags, item])}
                    selectedItems={selectedTags}
                    tagRenderer={tags => tags.tag}/>
                <Button><Search/></Button>
            </ControlGroup>
        </>
    );
}

export default SearchByTags