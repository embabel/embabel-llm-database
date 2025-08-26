import { useNavigate } from "react-router-dom";
import { Section, SectionCard } from "@blueprintjs/core";

import SearchByName from "../forms/SearchByName";

function Home() {

    const navigate = useNavigate();

    const handleSearch = (searchString) => {
        if (searchString) {
            navigate('/search',{state: { searchTerm: searchString }});//set and navigate
        }
    }

    return (
        <>
            <Section style={{ height: '100vh', display: 'grid', placeItems: 'center' }}>
                <SectionCard>
                    <SearchByName onSearch={handleSearch}/>
                </SectionCard>
            </Section>
        </>
    );
}

export default Home