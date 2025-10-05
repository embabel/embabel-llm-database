import { useState, useRef } from "react";
import { Section, SectionCard } from "@blueprintjs/core";

import ChatInput from "../forms/ChatInput";
const base_url = "/api/v1/models";

function Recommender() {

    const [messages, setMessages] = useState([]);

    const chatInputRef = useRef(null);

    const updateMessages = (message,role) => {
        //normalize the message object first
        let msg = {
            "id":crypto.randomUUID(),
            "role":role
        }
        console.log(message);
        if ("providers" in message) {
            //it's the provider message
            //split up the response
            let providers = message["providers"]
            //providers is a comma delimited array
            let split = providers.split(",");
            let formattedProviders = split.join(" ");
            msg.message = `Please reply with the name one of the following providers. ${formattedProviders}`
        } else if ("models" in message) {
            //it's the message with the full models
            //TODO format models
            msg.message = `Here are some model options`;
        } else {
            msg.message = message.message;
        } //end if
        //get the existing message set
        setMessages(prev => [...prev, msg]);
    }

    const sendMessage = (message) => {
        //update the value straight away
        updateMessages({"message":message},"user");//request
        //send the content of value
        fetch(`${base_url}/recommend`,{
            method:'POST',
            headers:{'Content-type': 'text/plain', 'Accept':'application/json'},
            body: message
        })
        .then(response => response.json())
        .then(data => {            
            updateMessages(data,"system");//reply
            if (chatInputRef.current) {
                chatInputRef.current.setThinkingState(false);
            }            
        });
    }

    return (
        <>
            <Section style={{  height: '100vh',
                        display: 'grid',
                        gridTemplateRows: '1fr auto',  // top scrollable, bottom fixed
                        overflow: 'hidden' }}>
                <SectionCard style={{ overflowY: 'auto',padding: '1rem' }}>
                    {messages.length > 0 &&
                        messages.map((msg) => (
                            <div
                                key={msg.id}
                                style={{
                                display: "flex",
                                justifyContent: msg.role === "user" ? "flex-end" : "flex-start",
                                margin: "0.5rem 0", // vertical spacing between messages
                                }} >
                                <div
                                    style={{
                                        padding: "0.5rem 1rem",
                                        borderRadius: "8px",
                                        color: "black",
                                        backgroundColor: msg.role === "user" ? "#d1e7ff" : "#f0f0f0",
                                        textAlign: msg.role === "user" ? "end" : "start",
                                        maxWidth: "70%",
                                    }} >
                                    {msg.message}
                                </div>
                            </div>                     
                        ))
                    }
                </SectionCard>
                <SectionCard style={{  borderTop: '1px solid #ccc' }}>
                    <ChatInput ref={chatInputRef} sendMessage={sendMessage}/>
                </SectionCard>
            </Section>
        </>
    );
}

export default Recommender


                    // <ControlGroup>
                    //     <InputGroup type="text" inputSize={50} style={{width: '60vw'}} value={value} onChange={(e) => setValue(e.target.value)} onKeyDown={handleKeyDown}/>
                    //     <Button icon="send-message" text="Send" onClick={sendMessage}/>
                    // </ControlGroup>