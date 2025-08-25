
import { Classes } from "@blueprintjs/core";
import { Cell, ColumnHeaderCell, Column, Table } from "@blueprintjs/table";

function renderColumnHeader(index) {
    const name = ["Name","Provider","Tokens","Tags"][index];
    return <ColumnHeaderCell name={name} index={index} nameRenderer={renderName}/>;
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
        return <Cell style={{ textAlign: "end" }}>{displayValue}</Cell>;    
    } else {
        columnName = "tags";
        // process tags a little differently
        const cellData = data[rowIndex] ? data[rowIndex][columnName] : '';
        let displayValue;
        displayValue = cellData.join(', ');
        return <Cell style={{ textAlign: "start" }}>{displayValue}</Cell>;    
    } //end if
    const cellData = data[rowIndex] ? data[rowIndex][columnName] : '';
    return <Cell style={{ textAlign: "start" }}>{cellData}</Cell>;
}

function ResultsTable({ data }) {

    return (
        <Table numRows={data.length} enableColumnResizing={true} columnWidths={[200,200,100,600]} style={{ height: '100vh', width: '90vw'}}>
            <Column cellRenderer={(rowIndex) => renderCell(rowIndex,0,data)} columnHeaderCellRenderer={renderColumnHeader}/>
            <Column cellRenderer={(rowIndex) => renderCell(rowIndex,1,data)} columnHeaderCellRenderer={renderColumnHeader}/>
            <Column cellRenderer={(rowIndex) => renderCell(rowIndex,2,data)} columnHeaderCellRenderer={renderColumnHeader}/>
            <Column cellRenderer={(rowIndex) => renderCell(rowIndex,3,data)} columnHeaderCellRenderer={renderColumnHeader}/>
        </Table>
    );
}

export default ResultsTable