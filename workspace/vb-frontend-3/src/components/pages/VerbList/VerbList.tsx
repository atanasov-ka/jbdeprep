import React from 'react';
import {RouteComponentProps} from 'react-router'
import TableBody from "@material-ui/core/TableBody/TableBody";
import TableRow from "@material-ui/core/TableRow/TableRow";
import TableHead from "@material-ui/core/TableHead/TableHead";
import Table from "@material-ui/core/Table/Table";
import TableCell from "@material-ui/core/TableCell/TableCell";
import Button from "@material-ui/core/Button/Button";
import DialogTitle from "@material-ui/core/DialogTitle/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent/DialogContent";
import TextField from "@material-ui/core/TextField/TextField";
import DialogActions from "@material-ui/core/DialogActions/DialogActions";
import Dialog from "@material-ui/core/Dialog/Dialog";
import IconButton from "@material-ui/core/IconButton/IconButton";
import AddCircleIcon from "@material-ui/icons/AddCircle";

import { Verb } from "../BoxList/BoxList";
import VbAppBar from "../../elements/VbAppBar/VbAppBar";
import "./VerbList.css"
import Fade from "@material-ui/core/Fade/Fade";

export type Verbs = {
    dialogOpen: boolean,
    categoryId:number,
    boxId: number,
    items: Array<Verb>
};

class VerbList extends React.Component<RouteComponentProps, Verbs> {
    constructor(props){
        super(props);

        this.componentDidMount = this.componentDidMount.bind(this);
        this.state = { boxId:0, categoryId:0, dialogOpen:false, items:[] };
    }

    componentDidMount() {
        const { boxId } = this.props.match.params;
        this.setState({ boxId:boxId });
        let url = `http://localhost:8081/vb/api/box/${boxId}/verb`;
        fetch(url, { method: "GET", headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + localStorage.getItem("authToken")
            }})
            .then(response => response.text())
            .then(json => {
                console.log(JSON.parse(json));
                this.setState({ items: JSON.parse(json) });
            })
            .catch(error => {
                //this.setState({error: error});
                console.error(error);
            });
    }

    handleCreate = () => {
        let url = 'http://localhost:8081/vb/api/box';
        fetch(url, { method: "GET", headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Basic ' + localStorage.getItem("authToken")
            }})
            .then(response => response.text())
            .then(json => {
                console.log(JSON.parse(json));
                let newBox = JSON.parse(json);
                let elems = this.state.items;
                elems.push(newBox);
                this.setState({ items:  elems});
            })
            .catch(error => {
                //this.setState({error: error});
                console.error(error);
            });
    };

    handleClickOpen = () => {
        this.setState({ dialogOpen: true });
    };

    handleClose = () => {
        this.setState({ dialogOpen: false });
    };

    setNewVerbFront = (event) => {
        // this.setState({newCategoryName: event.target.value});
    };

    private setNewVerbFrontTranscription() {

    }

    private setNewVerbBack() {

    }

    private setNewVerbBackTranscription() {

    }

    render() {
        return <div>
            <VbAppBar>
                <Button size="small" href={"/categories"}>Categories</Button>
                <IconButton color={"inherit"} onClick={this.handleClickOpen} aria-label={"Add Verb"}><AddCircleIcon /></IconButton>
            </VbAppBar>
            <Fade in={true}>
                <Table className={"table"}>
                    <TableHead>
                        <TableRow>
                            <TableCell align="left">Front</TableCell>
                            <TableCell align="left">Back</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {
                            this.state.items.map(function (verb:Verb) {
                                return <TableRow className="row" key={verb.id}>
                                    <TableCell component="th" scope="row" align="left">
                                        <b>{verb.front}</b><br/>
                                        [<i>{verb.frontTranscription}</i>]
                                    </TableCell>
                                    <TableCell component="th" scope="row" align="left">
                                        <b>{verb.back}</b><br/>
                                        [<i>{verb.backTranscription}</i>]
                                    </TableCell>
                                </TableRow>
                            })
                        }
                    </TableBody>
                </Table>
            </Fade>
            <Dialog open={this.state.dialogOpen} onClose={this.handleClose} aria-labelledby="form-dialog-title">
                <DialogTitle id="form-dialog-title">New verb</DialogTitle>
                <DialogContent>
                    <Table className={"table"}>
                        <TableBody>
                            <TableRow>
                                <TableCell>
                                    <TextField autoFocus margin="dense" id="front" label="Front" type="text" onChange={this.setNewVerbFront} fullWidth />
                                </TableCell>
                                <TableCell>
                                    <TextField autoFocus margin="dense" id="back" label="Back" type="text" onChange={this.setNewVerbBack} fullWidth />
                                </TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell>
                                    <TextField autoFocus margin="dense" id="frontTranscription" label="Front transcription" type="text" onChange={this.setNewVerbFrontTranscription} fullWidth />
                                </TableCell>
                                <TableCell>
                                    <TextField autoFocus margin="dense" id="backTranscription" label="Back transcription" type="text" onChange={this.setNewVerbBackTranscription} fullWidth />
                                </TableCell>
                            </TableRow>
                        </TableBody>
                    </Table>
                </DialogContent>
                <DialogActions>
                    <Button onClick={this.handleCreate} color="primary">Create</Button>
                </DialogActions>
            </Dialog>
        </div>
    }
}

export default VerbList;