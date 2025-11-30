import { useEffect, useState } from "react";
import { Section, SectionCard } from "@blueprintjs/core";

import SearchByTags from "../forms/search/SearchByTags";
import ResultsTable from "../data/table/ResultsTable";

import Model from "../data/model/Model";

const base_url = "/api/v1/models";

function Tags() {

    const [data, setData] = useState([]);
    const [model, setModel] = useState();

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
        try {
            const params = new URLSearchParams();
            tags.forEach(tag => {
                params.append("tags",tag.tag);
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
    },[]);

    const handleRowSelection = (region) => {
        var idx = region[0]['rows'][0];
        var modelId = data[idx]['modelId'];
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
                    <SearchByTags onSearch={handleSearch} onReset={handleReset} />
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