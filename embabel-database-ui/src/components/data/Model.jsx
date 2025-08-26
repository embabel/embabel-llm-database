import { Elevation, Card, Tag } from "@blueprintjs/core";

import './Model.css';

function Model({ model }) {

    return (
        <Card elevation={Elevation.ONE}>
            { model ? (
                <>
                    <table>
                        <tbody>
                            <tr>
                                <td className="emphasis-tag">Name:</td>
                                <td>{model.name || ""}</td>
                            </tr>
                            <tr>
                                <td className="emphasis-tag">Provider:</td>
                                <td>{model.provider || ""}</td>
                            </tr>
                            <tr>
                                <td className="emphasis-tag">Token Count:</td>
                                <td>{model.size || ""}</td>
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
                                <td colSpan="3">
                                    <hr/>
                                </td>
                            </tr>
                            <tr>
                                <td className="emphasis-tag" colSpan="3">
                                    Pricing per 1m Tokens ($USD)
                                </td>
                            </tr>
                            <tr>
                                <td className="emphasis-tag">
                                    Input:
                                </td>
                                <td>
                                    ${model.pricingModel.usdPer1mInputTokens}
                                </td>
                            </tr>
                            <tr>
                                <td className="emphasis-tag">
                                    Output:
                                </td>
                                <td>
                                      ${model.pricingModel.usdPer1mOutputTokens}
                                </td>
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