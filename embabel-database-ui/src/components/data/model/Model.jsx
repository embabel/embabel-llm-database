import { Elevation, Card, Tag } from "@blueprintjs/core";

import { formatPrice } from "../../../utils/formatPrice";

import { formatParameters } from "../../../utils/formatParameters";

import './Model.css';

function Model({ model }) {

    return (
        <Card elevation={Elevation.ONE} style={{height:'100%',width:'100%'}}>
            { model ? (
                <>
                    <table style={{width:'100%'}}>
                        <tbody>
                            <tr>
                                <td className="emphasis-tag">Name:</td>
                                <td>{model.name || ""}</td>
                            </tr>
                            <tr>
                                <td className="emphasis-tag">Token Count:</td>
                                <td>{model.parameterCount ? formatParameters(model.parameterCount) : ""}</td>
                            </tr>                              
                            <tr>
                                <td className="emphasis-tag">Tags:</td>
                                <td>{model.tags.map((value, idx) => (
                                    <span key={idx}>
                                        <Tag>{value}</Tag>&nbsp;
                                    </span>
                                ))}</td>
                            </tr>
                            <tr>
                                <td className="emphasis-tag">Description:</td>
                                <td>{model.description || ""}</td>
                            </tr>
                        </tbody>
                    </table>
                    <hr/>
                    <table style={{width:'100%'}}>
                        <tbody>
                            <tr>
                            {model.modelProviders ? model.modelProviders.map((modelProvider,idx) => (
                                <td key={idx}>
                                    <table>
                                        <tbody>
                                            <tr>
                                                <td className="emphasis-tag" colSpan="2">
                                                    {modelProvider.provider.name}
                                                </td>
                                            </tr>        
                                            <tr>
                                                <td className="emphasis-tag" colSpan="2">
                                                    Pricing per 1m Tokens ($USD)
                                                </td>
                                            </tr>
                                            <tr>
                                                <td className="emphasis-tag">
                                                    Input:
                                                </td>
                                                <td>
                                                    {modelProvider.inputPerMillion ? formatPrice(modelProvider.inputPerMillion) : null}
                                                </td>
                                            </tr>                                    
                                            <tr>
                                                <td className="emphasis-tag">
                                                    Output:
                                                </td>
                                                <td>
                                                    {modelProvider.outputPerMillion ? formatPrice(modelProvider.outputPerMillion) : null}
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </td>                  
                                )) : (
                                <></>
                            )}
                            </tr>
                        </tbody>
                    </table>
                </>
            ) : (
                <p></p>
            )}            
        </Card>
    );
}

export default Model