import { useEffect, useState } from 'react';
import { Button, Section, SectionCard } from "@blueprintjs/core";

import AppData from "../layout/AppData";

const base_url_agents = '/api/v1/agents'
const base_url_platform = '/api/v1/platform-info'
const base_url_process = '/api/v1/process'
const base_url_model = '/api/v1/models'

function Maintenance() {

    const [running,setRunning] = useState(false);
    const [agentId,setAgentId] = useState("");
    const [agents,setAgents] = useState([]);
    const [agentName,setAgentName] = useState("");

    const monitorStatus = (processId) => {
        let timeoutId;
        const interval = 5000; //5 seconds

        async function poll() {
            let notFound = false;
            try {
                console.log("polling");
                const response = await fetch(`${base_url_model}/refresh/${processId}`)
                if (!response.ok) {
                    if (response.status === 404) {
                        notFound = true;
                    }
                    throw new Error(`http error: ${response.status}`)
                };
                const data = await response.text()
                console.log(data);
                if (data !== 'STARTING' || data !== 'STARTED') {
                    //done
                    setRunning(false);//stop this part
                    //refresh the data block
                    return;
                } //end if
                //update the timeout
                timeoutId = setTimeout(poll,interval);
            } catch (error) {
                console.error('polling error:',error);
                if (notFound) {
                    setRunning(false);//stop this part
                    //done
                    return;
                }
                timeoutId = setTimeout(poll,interval);
            }            
        }

        poll();//poll function

        return () => cleaerTimeout(timeoutId);//reset
    }

    const handleStartAgent = () => {
        fetch(`${base_url_model}/refresh`, {
            method: 'POST',
            headers: {
                'Content-type':'application/json'
            }
        })
        .then(response => response.json())
        .then(data => {
            setRunning(!running); //flip
            setAgentId(data[0]);//set the return name
            //start a polling systems         
            console.log(data);
            // monitorStatus(data[0]);
            //executionid
            const executionId = data.executionId;
            monitorStatus(executionId);
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
        fetch(`${base_url_agents}/${agentName}/processes`)
        .then((response) => response.json())
        .then((data) => {
            data.forEach((process) => {
                fetch(`${base_url_process}/${process}`)
                .then((response) => response.text())
                .then((processData) => {
                    if (processData.includes("STARTING")) {
                        //there's one running
                        setRunning(true);
                        //exit
                    }
                })
            })
        })
    }

    const loadAgents = () => {
        fetch(`${base_url_platform}/agents`)
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