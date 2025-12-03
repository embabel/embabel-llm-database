import { useState, useRef } from "react";
import { Button, Section, SectionCard, TextAlignment } from "@blueprintjs/core";

import { ChatContainer, MessageList, MessageSeparator, Message, MessageInput, TypingIndicator } from '@chatscope/chat-ui-kit-react';

import Model from "../data/model/Model";

import ReactLinkify from "react-linkify";

const base_url = "/api/v1/models";

import '@chatscope/chat-ui-kit-styles/dist/default/styles.min.css';
import './Recommender.css';

function Recommender() {

    const [model, setModel] = useState();
    const [models, setModels] = useState([]);
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
                    position: "normal",
                    type: "html"
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

    const handleViewMoreClick = (id) => {
        //navigate to view more detail
        console.log('handle loading model from here ' + id);
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

    const sendMessage = (message) => {

        setThinking(true);

        fetch(`${base_url}/recommend`, {
            method: 'POST',
            headers: { 
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            ...(sessionId ? { 'x-embabel-request-id': sessionId } : {})
            },
            body: JSON.stringify({"message":message})
        })
        .then(async (response) => {
            // Read session id from response header instead of body
            const headerSessionId = response.headers.get('x-embabel-request-id');
            if (!headerSessionId) {
                throw new Error("No sessionId header returned");
            }
            setSessionId(headerSessionId);
            // If you still need the JSON body for other data:
            const data = await response.json();
            // handle `data` as needed here
            console.log(data);

            let msg = data;
            
            //process the message
            if (data.providers) {

                //build out the list
                let list = data.providers
                    .providers
                    .map((provider) => (<li key={provider}>{provider}</li>))

                let formattedMessage = (
                    <>
                        <span>{data.providers.message}</span>
                        <ul>{list}</ul>
                    </>
                )

                console.log(formattedMessage);

                //this is a list of providers
                msg = formatMessage(formattedMessage,"system");

            } else if (data.models) {
                const modelOptions = data.models.listModels.models;
                console.log(modelOptions);
                setModels(modelOptions);
                //this is 'models'
                let list = data.models
                    .listModels
                    .models
                    .map((model,idx) => (
                        <>
                        <Button variant="minimal" key={model.id} onClick={() => handleModel(model.id)} text={model.name}/>
                        <br/>
                        </>
                    ))

                let formattedMessage = (
                    <>
                        <p>{data.models.message}</p>
                        <br/>
                        {list}
                    </>
                )
                //this is a list of providers
                msg = formatMessage(formattedMessage,"system");
            }

            setMessages(prev => [...prev,msg]);

            setThinking(false);
        })
        .catch(error => {
            console.error("Error initiating recommendation:", error);
            setThinking(false);
        });
    };

    const handleModel = (id) => {
        //retrieve model
        fetch(`${base_url}/${id}`)
            .then(response => {
                if (!response.ok) {
                    if (response.status === 404) {
                        throw new Error('Model not found (404) ' + modelId);
                    } else {
                        throw new Error('Http error, status = ' + response.status);
                    }//end if
                } //end if
                return response.json()})
            .then(model => setModel(model))
            .catch(error => {
                console.error("failed to retrieve model: " + modelId,error);
            });
    }

    return (
        <Section
            style={{ height: '100%', display: 'grid', gridTemplateRows: '20% 50% 30%', placeItems: 'center'}}>
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
            <SectionCard style={{ height: '100%', width: '100%'}}>
                <Model model={model}/>
            </SectionCard>
        </Section>
    );
}

export default Recommender