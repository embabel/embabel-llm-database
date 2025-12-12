import { useEffect, useState } from "react";
import { Section, SectionCard } from "@blueprintjs/core";
import { useLocation } from "react-router-dom";

import SearchByTags from "../forms/search/SearchByTags";
import ResultsTable from "../data/table/ResultsTable";

import Model from "../data/model/Model";

const base_url = "/api/v1/models";

function Tags() {

    const [data, setData] = useState([]);
    const [model, setModel] = useState();


    const location = useLocation();
    const { tags } = location.state || {};    

    const fetchModels = async () => {
        try {
            const response = await fetch(`${base_url}`)
            const data = await response.json();
            setData(data);
        } catch (error) {
            console.error("Error loading models:",error);
        }
    }

    const searchModels = async (tags) => {
        if (!tags || tags.length === 0) {
            console.log("No tags provided — skipping fetch.");
            return;
        }

        try {
            const params = new URLSearchParams();
            console.log(tags);
            tags.forEach(tag => {
                params.append("tags",tag);
            })
            const url = `${base_url}/search/findByTags?${params.toString()}`;
            const response = await fetch(url);
            const data = await response.json();
            setData(data);
        } catch (error) {
            console.error("Error loading search string:",error);
        }
    }

    useEffect(() => {
        fetchModels();
        console.log(tags);
        if (tags) {
            searchModels(tags);
        }
    },[tags]);

    const handleRowSelection = (region) => {
        var idx = region[0]['rows'][0];
        var modelId = data[idx]['id'];
        //retrieve model
        fetch(`${base_url}/${modelId}`)
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

    const handleSearch = (tags) => {
        searchModels(tags);
    }

    const handleReset = () => {
        fetchModels();
    }    



    return (
        <>
            <Section style={{ height: '100vh', display: 'grid', placeItems: 'center', gridTemplateRows: '20% 40% 40%' }}>
                <SectionCard style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', }}>
                    {/* <SearchByTags onSearch={handleSearch} onReset={handleReset} /> */}
                    <h3>Models by Tags:</h3>
                </SectionCard>
                <SectionCard style={{ overflowY: 'auto', padding: '1rem', height: '100%', width: '90%'}}>
                    <ResultsTable data={data} selectionCallback={handleRowSelection} />
                </SectionCard>
                <SectionCard style={{ height: '100%', width: '90%'}}>
                    <Model model={model}/>
                </SectionCard>
            </Section>
        </>
    );
}

export default Tags