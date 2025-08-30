import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { EntityTitle, Section, SectionCard, Menu, MenuItem, MenuDivider } from "@blueprintjs/core";
import { Database } from "@blueprintjs/icons";
import { version } from '../../../package.json';

import AppData from './AppData';

function MenuBlock() {

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

    const handleGoProviders = () => {
        navigate('/providers');
    }

    const handleGoAgentMaintenance = () => {
        navigate('/agent/maintenance');
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
                    <Menu>
                        <MenuItem text="Home" onClick={handleGoHome}/>
                        <MenuItem text="Search">
                            <MenuItem text="By Name" onClick={handleGoSearch}/>
                            <MenuItem text="By Tags" onClick={handleGoTags}/>
                            <MenuItem text="By Provider" onClick={handleGoProviders}/>
                        </MenuItem>
                        <MenuItem text="Agents">
                            <MenuItem text="Maintenance" onClick={handleGoAgentMaintenance}/>
                        </MenuItem>
                    </Menu>

                </SectionCard>
            </div>
            <SectionCard style={{ position: 'sticky', bottom: 0, zIndex: 1, background: 'inherit' }}>
                <AppData showVersion={true} />
            </SectionCard>
        </Section>
    );
}

export default MenuBlock