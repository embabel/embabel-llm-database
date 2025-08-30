import { useEffect, useState } from 'react';
import { Button, Section, SectionCard } from "@blueprintjs/core";
import { Refresh } from "@blueprintjs/icons";

import AppData from "../layout/AppData";

function Maintenance() {

    const [running,setRunning] = useState(false);
    const [agentId,setAgentId] = useState("");
    const [agents,setAgents] = useState([]);
    const [agentName,setAgentName] = useState("");

    const handleStartAgent = () => {
        fetch(`/api/v1/agents/${agentName}`, {
            method: 'POST',
            headers: {
                'Content-type':'application/json'
            }
        })
        .then(response => response.text())
        .then(data => {
            setRunning(!running); //flip
            setAgentId(data);//set the return            
        })
    }

    const loadAgentName = (agents) => {
        agents.forEach((agent) => {
            if (agent.provider.includes("com.embabel.database.agent") 
                && agent.description.includes("loads AI models")) {
                    // this is the one we want
                    setAgentName(agent.name);
                    loadAgentStatus(agent.name);
            } //end if
        })
    }

    const loadAgentStatus = (agentName) => {
        //get the processes for the agents and check the status
        fetch(`/api/v1/agents/${agentName}/processes`)
        .then((response) => response.json())
        .then((data) => {
            data.forEach((process) => {
                fetch(`/api/v1/process/${process}`)
                .then((response) => response.json())
                .then((processData) => {
                    if (processData.status.includes("RUNNING")) {
                        //there's one running
                        setRunning(true);
                        //exit
                    }
                })
            })
        })
    }

    const loadAgents = () => {
        fetch('/api/v1/platform-info/agents')
        .then(response => response.json())
        .then(data => {
            setAgents(data); 
            loadAgentName(data)
        });
    }

    useEffect(() => {
        loadAgents();
    },[]);

    return (
        <>
            <Section style={{ height: '100vh', display: 'grid', placeItems: 'center' }}>                    
                <SectionCard>
                    <AppData/>                    
                    <br/>
                    <Button icon="refresh" text="Refresh Database" onClick={handleStartAgent}/>
                    <br/>
                    { (running) ? (<>
                        <br/>
                        <p>Running...</p>
                    </>) : (<>
                        <br/>
                        <p>&nbsp;</p></>)}                   
                </SectionCard>
            </Section>
        </>
    );
}

export default Maintenance;