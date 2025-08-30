
import { Classes, Tag } from "@blueprintjs/core";
import { Cell, ColumnHeaderCell, Column, Table } from "@blueprintjs/table";

import { formatPrice } from "../../utils/formatPrice";


const columnNames = ["Name","Provider","Tokens","Pricing per 1m Token","Tags"];

function renderColumnHeader(index) {
    // const name = ["Name","Provider","Tokens","Pricing per 1m Token","Tags"][index];
    const name = columnNames[index];
    const keyVal = name + "-" + index
    return <ColumnHeaderCell key={keyVal} name={name} index={index} nameRenderer={renderName}/>;
}

function renderName(name) {
    return (
        <div style={{ lineHeight: "24px"}}>
            <div className={Classes.TEXT_LARGE}>
                <strong>{name}</strong>
            </div>
        </div>
    )
}

function renderCell(rowIndex, columnIndex, data) {
    const cellKey = data[rowIndex]["modelId"] + "-" + columnIndex
    //use the column index to get the right column
    var columnName = "";
    if (columnIndex === 0) {
        //first column is name
        columnName = "name";
    } else if (columnIndex === 1) {
        columnName = "provider";
    } else if (columnIndex === 2) {
        columnName = "size";
        const cellData = data[rowIndex] ? data[rowIndex][columnName] : '';
        let displayValue;
        displayValue = cellData.toLocaleString();
        return (
            <Cell key={cellKey} style={{ textAlign: "end" }}>{displayValue}</Cell>);   
    } else if (columnIndex === 3) {
        columnName = "pricingModel";
        //format to contain both input and output pricing
        const cellData = data[rowIndex] ? data[rowIndex][columnName] : '';        
        const inputValue = `Input: ${formatPrice(cellData.usdPer1mInputTokens)}`
        const outputValue = `Output: ${formatPrice(cellData.usdPer1mOutputTokens)}`
        return (
            <Cell key={cellKey} style={{ textAlign: "justify" }}>
                {inputValue}&nbsp;{outputValue}
            </Cell>   
        );
    } else {
        columnName = "tags";
        // process tags a little differently
        const cellData = data[rowIndex] ? data[rowIndex][columnName] : '';
        return (
            <Cell key={cellKey} style={{ textAlign: "start" }}>
                {Array.isArray(cellData) ? (cellData.map((value, idx) => (
                    <span key={idx}>
                        <Tag>{value}</Tag>&nbsp;
                    </span>
                ))
                ) : (
                    <Tag>{cellData}</Tag>
                )}
            </Cell>);
    } //end if
    //use the column name
    const cellData = data[rowIndex] ? data[rowIndex][columnName] : '';
    return (<Cell key={cellKey} style={{ textAlign: "start" }}>{cellData}</Cell>);
}

function ResultsTable({ data, selectionCallback }) {

    const selectedColumns = [0,1,2,3,4]
    const columnWidths = [200,100,100,200,500];
    const totalWidth = columnWidths.reduce((a,b) => a + b, 0);

    const handleSelection = (region) => {
        if (selectionCallback) {
            //invoke selection callback
            selectionCallback(region);
        }
    }

    return (
        <Table numRows={data.length} onSelection={handleSelection} enableMultipleSelection={false} enableColumnResizing={true} columnWidths={columnWidths} defaultColumnWidth={100} style={{ height: '100vh'}}>
            <Column key="col-0" cellRenderer={(rowIndex) => renderCell(rowIndex,0,data)} columnHeaderCellRenderer={renderColumnHeader}/>
            <Column key="col-1" cellRenderer={(rowIndex) => renderCell(rowIndex,1,data)} columnHeaderCellRenderer={renderColumnHeader}/>
            <Column key="col-2" cellRenderer={(rowIndex) => renderCell(rowIndex,2,data)} columnHeaderCellRenderer={renderColumnHeader}/>
            <Column key="col-3" cellRenderer={(rowIndex) => renderCell(rowIndex,3,data)} columnHeaderCellRenderer={renderColumnHeader}/>
            <Column key="col-4" cellRenderer={(rowIndex) => renderCell(rowIndex,4,data)} columnHeaderCellRenderer={renderColumnHeader}/>
        </Table>
    );
}

export default ResultsTable
