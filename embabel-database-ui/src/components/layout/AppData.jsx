import { useEffect, useState } from 'react';
import { version } from '../../../package.json';

import { formatDate } from "../../utils/formatDate";

function AppData() {
    const [lastUpdated, setLastUpdated] = useState("");
    const [recordCount, setRecordCount] = useState("");

    const loadUpdatedDate = () => {
        fetch("/api/v1/models/lastUpdated")
        .then(response => response.text())
        .then(data => setLastUpdated(data));
    }

    const loadRecordCount = () => {
        fetch("/api/v1/models/count")
        .then(response => response.json())
        .then(data => setRecordCount(data["count"]));
    }

    useEffect(() => {
        loadUpdatedDate();
        loadRecordCount();
    },[]);

    return (
        <>
            <div style={{ textAlign: "start "}}>
                <p><strong>Record Count:</strong> </p>
                <p>{recordCount} </p>
                <p><strong>Database Last Updated:</strong> </p>
                <p>{formatDate(lastUpdated)}</p>                
                <p><strong>Version:</strong></p>
                <p> v{version}</p>
            </div>        
        </>
    );
}

export default AppData;