import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { EntityTitle, Section, SectionCard, Menu, MenuItem, MenuDivider } from "@blueprintjs/core";
import { Database } from "@blueprintjs/icons";
import { version } from '../../../package.json';

import AppData from './AppData';

function MenuBlock() {

    const [lastUpdated, setLastUpdated] = useState("");

    const navigate = useNavigate();
    
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
                        <MenuItem text="Home" onClick={() => navigate('/')}/>
                        <MenuItem text="Search">
                            <MenuItem text="By Name" onClick={() => navigate('/search')}/>
                            <MenuItem text="By Tags" onClick={() => navigate('/tags')}/>
                            <MenuItem text="By Provider" onClick={() => navigate('/providers')}/>
                        </MenuItem>
                        <MenuItem text="Agents">
                            <MenuItem text="Maintenance" onClick={() => navigate('/agent/maintenance')}/>
                            <MenuItem text="Recommender" onClick={() => navigate('/agent/recommender')}/>
                        </MenuItem>
                    </Menu>
                </SectionCard>
            </div>
            <SectionCard style={{ position: 'sticky', bottom: 0, zIndex: 1, background: 'inherit' }}>
                <AppData showVersion={true} showRefresh={true}/>
            </SectionCard>
        </Section>
    );
}

export default MenuBlock