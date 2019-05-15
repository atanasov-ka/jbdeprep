import React from 'react';
import {RouteComponentProps} from 'react-router'
import Flag from 'react-world-flags'
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
import Fade from "@material-ui/core/Fade/Fade";
import LockIcon from "@material-ui/icons/Lock";
import LockOpenIcon from "@material-ui/core/SvgIcon/SvgIcon";
import Typography from "@material-ui/core/Typography/Typography";
import LinearProgress from "@material-ui/core/LinearProgress/LinearProgress";

import VbAppBar from "../../elements/VbAppBar/VbAppBar";
import {Box, Verb} from "../BoxList/BoxList";
import "./VerbList.css"

export type Verbs = {
    dialogOpen: boolean,
    categoryId:number,
    boxId: number,
    box?: Box,
    newVerbFront:string,
    newVerbBack:string,
    newVerbFrontTranscription:string,
    newVerbBackTranscription:string
};

class VerbList extends React.Component<RouteComponentProps, Verbs> {
    constructor(props){
        super(props);
        this.componentDidMount = this.componentDidMount.bind(this);
        this.state = { boxId:0, categoryId:0, dialogOpen:false,
            newVerbFront:"", newVerbBack:"", newVerbFrontTranscription:"", newVerbBackTranscription:""};
    }

    componentDidMount() {
        const { boxId } = this.props.match.params;
        this.setState({ boxId:boxId });
        let url = `http://${process.env.REACT_APP_BACKEND_HOST}:8081/vb/api/box/${boxId}`;
        fetch(url, { method: "GET", headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + localStorage.getItem("authToken")
            }})
            .then(response => response.text())
            .then(json => {
                console.log(JSON.parse(json));
                this.setState({ box: JSON.parse(json) });
            })
            .catch(error => {
                //this.setState({error: error});
                console.error(error);
            });
    }

    handleCreate = () => {
        let url = `http://${process.env.REACT_APP_BACKEND_HOST}:8081/vb/api/box/${this.state.boxId}`;
        fetch(url, { method: "POST", headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Basic ' + localStorage.getItem("authToken")
            }, body: JSON.stringify(
                {
                    id:this.state.boxId,
                    verbList:[
                        {
                            front: this.state.newVerbFront,
                            back: this.state.newVerbBack,
                            frontTranscription: this.state.newVerbFrontTranscription,
                            backTranscription: this.state.newVerbBackTranscription,
                            frontAudio: "",
                            backAudio: ""
                        }
                    ]
                })})
            .then(response => response.text())
            .then(json => {
                console.log(JSON.parse(json));
                let newBox = JSON.parse(json);
                this.setState({box: newBox});
                this.setState({dialogOpen:false});
            })
            .catch(error => {
                //this.setState({error: error});
                console.error(error);
                this.setState({dialogOpen:false});
            });
    };

    handleClickOpen = () => {
        this.setState({ dialogOpen: true });
    };

    handleClose = () => {
        this.setState({ dialogOpen: false });
    };

    setNewVerbFront = (event) => {
        this.setState({newVerbFront: event.target.value});
    };

    setNewVerbFrontTranscription = (event) => {
        this.setState({newVerbFrontTranscription: event.target.value});
    };

    setNewVerbBack = (event) => {
        this.setState({newVerbBack: event.target.value});
    };

    setNewVerbBackTranscription = (event) => {
        this.setState({newVerbBackTranscription: event.target.value});
    };

    private renderVerbList() {
        if (this.state.box != null) {
            return this.state.box.verbList.map(function (verb: Verb) {
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
        } else {
            return <TableRow/>
        }
    }

    render() {
        if (null != this.state.box) {
            return <div>
                <VbAppBar>
                    <Button size="small" href={"/categories"}>Categories</Button>
                    <IconButton color={"inherit"} onClick={this.handleClickOpen} aria-label={"Add Verb"}><AddCircleIcon /></IconButton>
                </VbAppBar>
                <div>
                    <Typography className={"title"} variant="h5" component="h2">
                        <span>{this.state.box.name}</span>
                        <Flag className={"flag"} code={ this.state.box.front } />
                        <Flag className={"flag"} code={ this.state.box.back } />
                        {this.state.box.public ? <LockOpenIcon /> : <LockIcon />}
                    </Typography>
                    <Typography>{this.state.box.verbCount} verbs</Typography>
                    <Typography>Progress Front</Typography>
                    <LinearProgress color="primary" variant="determinate" value={this.state.box.progressFront} />
                    <br />
                    <Typography>Progress Back</Typography>
                    <LinearProgress color="primary" variant="determinate" value={this.state.box.progressBack} />
                    <br />
                    <Typography>Level Back [{this.state.box.levelBackLow}/{this.state.box.levelBackMid}/{this.state.box.levelBackHigh}]</Typography>
                    <Typography>Level Front [{this.state.box.levelFrontLow}/{this.state.box.levelFrontMid}/{this.state.box.levelFrontHigh}]</Typography>
                    <br />
                    <Typography>Created by <b>{this.state.box.owner}</b> at: {new Date(this.state.box.created).toLocaleString()}</Typography>
                </div>
                <Fade in={true}>
                    <Table className={"table"}>
                        <TableHead>
                            <TableRow>
                                <TableCell align="left"><Flag className={"flag"} code={ this.state.box.front } />Front</TableCell>
                                <TableCell align="left"><Flag className={"flag"} code={ this.state.box.back } />Back</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>{this.renderVerbList()}</TableBody>
                    </Table>
                </Fade>
                <Dialog open={this.state.dialogOpen} onClose={this.handleClose} aria-labelledby="form-dialog-title">
                    <DialogTitle id="form-dialog-title">New verb</DialogTitle>
                    <DialogContent>
                        <Table className={"table"}>
                            <TableBody>
                                <TableRow>
                                    <TableCell>
                                        <Flag className={"flag"} code={ this.state.box.front }/><br/>
                                        <span>{ this.state.box.front }</span>
                                    </TableCell>
                                    <TableCell>
                                        <Flag className={"flag"} code={ this.state.box.back }/><br/>
                                        <span>{ this.state.box.back }</span>
                                    </TableCell>
                                </TableRow>
                                <TableRow>
                                    <TableCell>
                                        <TextField autoFocus margin="dense" id="front" label="Front" type="text" onChange={this.setNewVerbFront} fullWidth />
                                    </TableCell>
                                    <TableCell>
                                        <TextField margin="dense" id="back" label="Back" type="text" onChange={this.setNewVerbBack} fullWidth />
                                    </TableCell>
                                </TableRow>
                                <TableRow>
                                    <TableCell>
                                        <TextField margin="dense" id="frontTranscription" label="Front transcription" type="text" onChange={this.setNewVerbFrontTranscription} fullWidth />
                                    </TableCell>
                                    <TableCell>
                                        <TextField margin="dense" id="backTranscription" label="Back transcription" type="text" onChange={this.setNewVerbBackTranscription} fullWidth />
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
        } else {
            return <div/>
        }
    }
}

export default VerbList;