
import { Section, SectionCard } from "@blueprintjs/core";

import SearchByName from "../forms/SearchByName";

function Home() {
    return (
        <>
            <Section style={{ height: '100vh', display: 'grid', placeItems: 'center' }}>
                <SectionCard>
                    <SearchByName/>
                </SectionCard>
            </Section>
        </>
    );
}

export default Home