
import { Classes, Tag } from "@blueprintjs/core";
import { Cell, ColumnHeaderCell, Column, Table } from "@blueprintjs/table";

import { formatParameters } from "../../../utils/formatParameters";

const columnNames = ["Name","Providers","Parameters","Tags","Description"];

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
    console.log(columnName);
    var columnName = "";
    if (columnIndex === 0) {
        //first column is name
        columnName = "name";
    } else if (columnIndex === 1) {
        columnName = "modelProviders";
        //get the "modelProviders[n].provider.name"
        const cellData = data[rowIndex] ? data[rowIndex][columnName] : ''
        let providerNames = '';
        if (Array.isArray(cellData) && cellData.length > 0) {
            // Filter out undefined or falsy names and join with commas
            providerNames = cellData.map(modelProvider => modelProvider.provider?.name)  // optional chaining to prevent errors
                .filter(name => name !== undefined && name !== '')  // keep only defined, non-empty names
                .join(', ');  // comma delimited string            
        } //end if
        return (<Cell key={cellKey}>{providerNames}</Cell>);

    } else if (columnIndex === 2) {
        columnName = "parameterCount";
        const cellData = data[rowIndex] ? data[rowIndex][columnName] : '';
        let displayValue = formatParameters(cellData);
        return (
            <Cell key={cellKey} style={{ textAlign: "end" }}>{displayValue}</Cell>);   
    } else if (columnIndex === 3) {
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
    } else {
        columnName = "description";
        const cellData = data[rowIndex] ? data[rowIndex][columnName] : '';
        return (<Cell key={cellKey}>{cellData}</Cell>);
    } //end if
    //use the column name
    const cellData = data[rowIndex] ? data[rowIndex][columnName] : '';
    return (<Cell key={cellKey} style={{ textAlign: "start" }}>{cellData}</Cell>);
}

function ResultsTable({ data, selectionCallback }) {

    const columnWidths = [200,200,100,200,500];

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
