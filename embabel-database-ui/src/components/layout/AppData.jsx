import { useEffect, useState } from 'react';
import { Button } from "@blueprintjs/core";

import { version } from '../../../package.json';

import { formatDate } from "../../utils/formatDate";

const base_url = '/api/v1/models';

function AppData({ showVersion, showRefresh }) {
    const [lastUpdated, setLastUpdated] = useState("");
    const [recordCount, setRecordCount] = useState("");

    const loadUpdatedDate = () => {
        fetch(`${base_url}/lastUpdated`)
        .then(response => response.text())
        .then(data => setLastUpdated(data));
    }

    const loadRecordCount = () => {
        fetch(`${base_url}/count`)
        .then(response => response.json())
        .then(data => setRecordCount(data["count"]));
    }

    const handleRefresh = () => {
        //refresh
        loadRecordCount();
        loadUpdatedDate();
    }

    useEffect(() => {
        loadUpdatedDate();
        loadRecordCount();
    },[]);

    return (
        <>
            <div style={{ textAlign: "start "}}>
                <h4>Record Count {(showRefresh) ? (<><Button icon="refresh" onClick={handleRefresh} /></>) : (<></>)}</h4>
                <p>{recordCount} </p>
                <h4>Database Last Updated</h4>
                <p>{formatDate(lastUpdated)}</p>  
                {(showVersion) ? (
                    <>
                        <h4>Version</h4>
                        <p style={{ display: "flex", alignItems: "center", gap: "10px" }}> v{version} 
                            <a href="https://github.com/embabel" target="_blank" rel="noreferrer">
                                <img src="/github-mark-white.svg" style={{height: "20px"}}/>
                            </a>
                        </p>
                    </>
                ) : (<></>)}
            </div>        
        </>
    );
}

export default AppData;