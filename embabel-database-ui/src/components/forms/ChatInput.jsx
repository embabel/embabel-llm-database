import { useState, forwardRef, useImperativeHandle } from "react";
import { Button, ControlGroup, InputGroup} from "@blueprintjs/core";

const ChatInput = forwardRef(({sendMessage}, ref) => {

    const [value, setValue] = useState("");
    const [thinking, setThinking] = useState(false);

    useImperativeHandle(ref, () => ({
        setThinkingState: (newState) => {
            setThinking(newState);
        }
    }));

    const handleKeyDown = (e) => {
        if (e.key === "Enter" && thinking === false) {
            handleSend();
        }
    }

    const handleSend = () => {
        if (thinking) {
            console.log('still thinking...');
            //don't do anything
            return;
        } //end if
        //invoke send message
        if (sendMessage) {
            sendMessage(value);
            setThinking(true);
        } //end if
    }    

    return (
        <ControlGroup>
            <InputGroup type="text" inputSize={50} style={{width: '60vw'}} value={value} onChange={(e) => setValue(e.target.value)} onKeyDown={handleKeyDown}/>
            {thinking ? (
                <Button loading={thinking}>Thinking...</Button>
            ) : (
                <Button icon="send-message" text="Send" onClick={handleSend}/>
            )}            
        </ControlGroup>        
    );
});

export default ChatInput