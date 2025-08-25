import { useEffect, useState } from "react";
import { Section, SectionCard } from "@blueprintjs/core";
import SearchByName from "../forms/SearchByName";

import ResultsTable from "../data/ResultsTable";

function Search() {

    const [data, setData] = useState([]);

    useEffect(() => {
        async function fetchModels() {
            try {
                const response = await fetch("/api/v1/models")
                const data = await response.json();
                setData(data);
            } catch (error) {
                console.error("Error loading models:",error);
            }
        }

        fetchModels();
    },[]);

    return (
        <>
            <Section style={{ height: '100vh', display: 'grid', placeItems: 'center', gridTemplateRows: '20% 1fr' }}>
                <SectionCard style={{ display: 'flex'}}>
                    <SearchByName/>
                </SectionCard>
                <SectionCard style={{ overflowY: 'auto', padding: '1rem', height: 'calc(80vh - 1rem)', width: '90vw'}}>
                    <ResultsTable data={data}/>
                </SectionCard>
            </Section>
        </>
    );
}

export default Search
