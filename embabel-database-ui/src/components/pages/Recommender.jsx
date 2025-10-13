import { useState, useRef } from "react";
import { Section, SectionCard } from "@blueprintjs/core";
import ReactMarkdown from 'react-markdown';

import ChatInput from "../forms/ChatInput";
const base_url = "/api/v1/models";

function Recommender() {

    const [messages, setMessages] = useState([]);
    const [sessionId, setSessionId] = useState("");

    const chatInputRef = useRef(null);

    const updateMessages = (message,role) => {
        //normalize the message object first
        let msg = {
            "id":crypto.randomUUID(),
            "role":role
        }
        console.log(message);
        if (message.message) {
            msg.message = message.message;
        } else {
            msg.message = message;    
        }
        // } //end if
        //get the existing message set
        setMessages(prev => [...prev, msg]);
    }

    const sendMessage = (message) => {
        // Immediately show user's message
        updateMessages({ message }, "user");

        if (chatInputRef.current) {
            chatInputRef.current.setThinkingState(true);
        }

        // First POST to initiate recommendation session
        fetch(`${base_url}/recommend`, {
            method: 'POST',
            headers: { 
                'Content-Type': 'text/plain',
                'Accept': 'application/json',
                ...(sessionId ? { 'x-embabel-request-id': sessionId } : {})                
            },
            body: message
        })
        .then(response => response.json())
        .then(data => {
            const sessionId = data.sessionId;
            if (!sessionId) {
                throw new Error("No sessionId returned");
            }
            setSessionId(sessionId);
            
            // Start polling for results
            pollForResults(sessionId);
        })
        .catch(error => {
            console.error("Error initiating recommendation:", error);
            if (chatInputRef.current) {
                chatInputRef.current.setThinkingState(false);
            }
        });
    };

    const pollForResults = (sessionId) => {
        const pollInterval = 2000; // ms

        const poll = () => {
            fetch(`${base_url}/recommend/${sessionId}`, {
                method: 'GET',
                headers: { 
                    'Accept': 'application/json',
                    'x-embabel-request-id': sessionId
                }
            })
            .then(response => response.json())
            .then(data => {
                if (data.result) {
                    // Got a result, stop polling
                    updateMessages(data.result, "system");
                    if (chatInputRef.current) {
                        chatInputRef.current.setThinkingState(false);
                    }
                } else {
                    // Continue polling until result is ready
                    setTimeout(poll, pollInterval);
                }
            })
            .catch(error => {
                console.error("Error polling for results:", error);
                if (chatInputRef.current) {
                    chatInputRef.current.setThinkingState(false);
                }
            });
        };

        poll();
    };

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
                                    <ReactMarkdown>{msg.message}</ReactMarkdown>
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