import { useEffect, useState } from 'react';
import { useLocation, useNavigate } from "react-router-dom";
import { Checkbox } from "@blueprintjs/core";


function Tags() {

    const [tags,setTags] = useState([]);
    const [selectedTags, setSelectedTags] = useState([]);
    const location = useLocation();
    const navigate = useNavigate();

    //load tags
    const fetchTags = async () => {
        fetch("/api/v1/tags")
        .then(response => response.json())
        .then(data => setTags(data))
        .catch(err => console.error(err));
    } 
    
    const handleTagChange = (tag, isChecked) => {
        let updatedTags;
        if (isChecked) {
            // Add if checked
            updatedTags = [...selectedTags, tag];
        } else {
            // Remove if unchecked
            updatedTags = selectedTags.filter((t) => t !== tag);
        }
        setSelectedTags(updatedTags);
    };

    useEffect(() => {        
        fetchTags();
    },[location.pathname]);
    
    useEffect(() => {
        if (location.pathname.includes("tags")) {
            navigate(`/tags`, { state: { tags: selectedTags } });
        }
    }, [selectedTags, navigate, location.pathname]);    

    if (!location.pathname.includes("tags")) {
        return null;
    }

    return (
        (tags ? tags.map((tag,idx) => (<Checkbox key={idx} 
            label={tag} 
            checked={selectedTags.includes(tag)}
            onClick={(e)=>handleTagChange(tag,e.target.checked)}/>)
        ) : (
            <>
            </>
        ))
    );

}

export default Tags