import { useState, useRef } from "react";
import { Card, Section, SectionCard, TextAlignment } from "@blueprintjs/core";

import { ChatContainer, MessageList, MessageSeparator, Message, MessageInput, TypingIndicator } from '@chatscope/chat-ui-kit-react';

const base_url = "/api/v1/models";

import '@chatscope/chat-ui-kit-styles/dist/default/styles.min.css';
import './Recommender.css';

function Recommender() {

    const [messages, setMessages] = useState([]);
    const [sessionId, setSessionId] = useState("");
    const [thinking,setThinking] = useState(false);

    const chatInputRef = useRef(null);

    const formatMessage = (message,sender) => {
        const messageObj = {
            props: {
                model: {
                    message: message,
                    sender: sender,
                    direction: sender === "user" ? "outgoing" : "incoming",
                    position: "normal"
                },
                children: [
                    <Message.CustomContent>
                        <ReactLinkify componentDecorator={(decoratedRef, descroatedText, key) => <Button text="View More" onClick={handleViewMoreClick} key={key}/>}>
                            {message}
                        </ReactLinkify>
                    </Message.CustomContent>
                ]
            }
        }
        return messageObj;
    }

    const handleViewMoreClick = () => {
        //navigate to view more detail
    }

    const handleMessageSend = (message) => {
        //show working
        setThinking(true);
        //format message
        let msg = formatMessage(message,"user");
        //add to the list
        setMessages(prev => [...prev,msg]);
        //send
        sendMessage(message);
    }

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
        <Section
            style={{ height: '100%', display: 'grid', gridTemplateRows: '20% 80%', placeItems: 'center'}}>
            <SectionCard style={{ width: '100%'}}>
                <h3>Embabel LLM Recommender</h3>
            </SectionCard>
            <SectionCard style={{width:'100%', height:'100%', backgroundColor: '#252A31'}}>
                <ChatContainer style={{ height: '100%', overflow:'auto'}}>
                    <MessageList style={{backgroundColor:'#252A31', color:'white'}} typingIndicator={thinking ? <TypingIndicator style={{ textAlign:'start',backgroundColor:'#252A31', color:'white'}} content="Thinking..." /> : null}>
                        {messages.map((msg, idx) =>
                            msg.type === 'separator' ? (
                                <MessageSeparator key={idx} {...msg.props} />
                            ) : (
                                <Message key={idx} {...msg.props} />
                            )
                        )}
                    </MessageList>
                    <MessageInput style={{ textAlign:'start',backgroundColor:'#252A31', color:'white'}}
                        placeholder="Enter what you want the model to do..."
                        attachButton={false}
                        onSend={handleMessageSend}/>
                </ChatContainer>
            </SectionCard>
        </Section>
    );
}

export default Recommender