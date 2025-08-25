import { useEffect, useState } from "react";
import { Section, SectionCard } from "@blueprintjs/core";

import SearchByTags from "../forms/SearchByTags";
import ResultsTable from "../data/ResultsTable";

function Tags() {

    const [data, setData] = useState([]);

    return (
        <>
            <Section style={{ height: '100vh', display: 'grid', placeItems: 'center', gridTemplateRows: '20% 1fr' }}>
                <SectionCard style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', }}>
                    <SearchByTags/>
                </SectionCard>
                <SectionCard style={{ overflowY: 'auto', padding: '1rem', }}>
                    <ResultsTable data={data}/>
                </SectionCard>
            </Section>

        </>
    );
}

export default Tags