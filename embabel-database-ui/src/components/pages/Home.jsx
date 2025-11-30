import { useNavigate } from "react-router-dom";
import { Section, SectionCard } from "@blueprintjs/core";

import SearchByName from "../forms/search/SearchByName";

function Home() {

    const navigate = useNavigate();

    const handleSearch = (searchString) => {
        if (searchString) {
            navigate('/search',{state: { searchTerm: searchString }});//set and navigate
        } else {
            navigate('/search'); //just go directly
        }
    }

    return (
        <>
            <Section style={{ height: '100vh', display: 'grid', placeItems: 'center' }}>
                <SectionCard style={{height: '10%'}}>
                    <h3>Welcome to the Embabel LLM Database</h3>
                </SectionCard>
                <SectionCard style={{height: '90%'}}>
                    <SearchByName onSearch={handleSearch}/>
                </SectionCard>
            </Section>
        </>
    );
}

export default Home