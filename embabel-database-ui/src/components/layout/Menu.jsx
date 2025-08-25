import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Button, ButtonGroup, EntityTitle, Section, SectionCard } from "@blueprintjs/core";
import { Database } from "@blueprintjs/icons";
import { version } from '../../../package.json';

function Menu() {

    const [lastUpdated, setLastUpdated] = useState("");
    const [recordCount, setRecordCount] = useState("");

    const navigate = useNavigate();

    const handleGoSearch = () => {
        navigate('/search');
    }

    const handleGoTags = () => {
        navigate('/tags');
    }

    const handleGoHome = () => {
        navigate('/');
    }
    
    useEffect(() => {
        async function loadUpdatedDate() {
            try {
                const response = await fetch("/api/v1/models/lastUpdated")
                const data = await response.text();
                var dt = new Date(lastUpdated);
                var userLocale = navigator.language || undefined;
                var formatted = dt.toLocaleString(dt,userLocale);
                setLastUpdated(formatted);
            } catch (error) {
                console.error("Error loading models:",error);
            }
        }

        async function loadCount() {
            try {
                const response = await fetch("/api/v1/models/count")
                const data = await response.json();
                setRecordCount(data["count"]);
            } catch (error) {
                console.error("Error loading models:",error);
            }            
        }

        loadUpdatedDate();
        loadCount();
    },[]);

    return (
        <Section style={{ height: '100vh', display: 'flex', flexDirection: 'column' }}>
            <SectionCard>
                <EntityTitle title="Embabel LLM Database" icon={<Database/>}/>
            </SectionCard>
            <div style={{ flex: 1, display: 'flex', flexDirection: 'column' }}>
                <SectionCard>
                    <ButtonGroup vertical={true} fill={true}>
                        <Button onClick={handleGoHome}>Home</Button>
                        <Button onClick={handleGoSearch}>Search</Button>
                        <Button onClick={handleGoTags}>Tags</Button>
                        <Button>Providers</Button>
                    </ButtonGroup>
                </SectionCard>
            </div>
            <SectionCard style={{ position: 'sticky', bottom: 0, zIndex: 1, background: 'inherit' }}>
                <div style={{ textAlign: "start "}}>
                    <p><strong>Record Count:</strong> </p>
                    <p>{recordCount} </p>
                    <p><strong>Database Last Updated:</strong> </p>
                    <p>{lastUpdated}</p>
                    <p><strong>Version:</strong></p>
                    <p> v{version}</p>
                </div>
            </SectionCard>
        </Section>
    );
}

export default Menu