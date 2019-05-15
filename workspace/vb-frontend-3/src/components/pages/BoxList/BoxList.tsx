import React from 'react';
import {RouteComponentProps} from 'react-router'
import Button from "@material-ui/core/Button/Button";
import DialogActions from "@material-ui/core/DialogActions/DialogActions";
import TextField from "@material-ui/core/TextField/TextField";
import DialogContent from "@material-ui/core/DialogContent/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle/DialogTitle";
import Dialog from "@material-ui/core/Dialog/Dialog";
import GridList from "@material-ui/core/GridList/GridList";
import IconButton from "@material-ui/core/IconButton/IconButton";
import CreateNewFolderIcon from "@material-ui/icons/CreateNewFolder";

import BoxComponent from "../../elements/Box/BoxComponent";
import VbAppBar from "../../elements/VbAppBar/VbAppBar";

export type Boxes = {
    categoryId:number,
    dialogOpen: boolean,
    newBoxName: string,
    items : Array<Box>
};

export type Box = {
    "id":number,
    "name":string,
    "front":string,
    "back":string,
    "owner":string,
    "verbList":Array<Verb>,
    "verbCount":number,
    "categoryId":number,
    "created":number,
    "progressFront":number,
    "progressBack":number,
    "levelBackHigh":number,
    "levelBackMid":number,
    "levelBackLow":number,
    "levelFrontHigh":number,
    "levelFrontMid":number,
    "levelFrontLow":number,
    "lastPlayDate":number,
    "public":boolean
};

export type Verb = {
    "id":number,
    "front":string,
    "back":string,
    "frontAudio":string,
    "backAudio":string,
    "frontTranscription":string,
    "backTranscription":string,
    "created":number
};

class BoxList extends React.Component<RouteComponentProps, Boxes> {
    constructor(props){
        super(props);

        this.componentDidMount = this.componentDidMount.bind(this);
        this.state = {categoryId:0, dialogOpen:false, newBoxName:"", items:[]};
    }

    componentDidMount() {
        const { categoryId } = this.props.match.params;
        this.setState({categoryId:categoryId});
        let url = `http://${process.env.REACT_APP_BACKEND_HOST}:8081/vb/api/box/byGroup/${categoryId}`;
        fetch(url, { method: "GET", headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + localStorage.getItem("authToken")
            }})
            .then(response => response.text())
            .then(json => {
                console.log(JSON.parse(json));
                this.setState({items:JSON.parse(json)});
            })
            .catch(error => {
                //this.setState({error: error});
                console.error(error);
            });
    }

    handleClickOpen = () => {
        this.setState({ dialogOpen: true });
    };

    handleClose = () => {
        this.setState({ dialogOpen: false });
    };

    setnewBoxName = (event) => {
        this.setState({newBoxName: event.target.value});
    };

    handleCreate = () => {
        let url = 'http://${process.env.REACT_APP_BACKEND_HOST}:8081/vb/api/box';
        fetch(url, { method: "POST", headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Basic ' + localStorage.getItem("authToken")
            }, body: JSON.stringify({
                name: this.state.newBoxName,
                categoryId: this.state.categoryId,
                front: "BG", back: "EN"})})
            .then(response => response.text())
            .then(json => {
                console.log(JSON.parse(json));
                let newBox = JSON.parse(json);
                let elems = this.state.items;
                elems.push(newBox);
                this.setState({ items:  elems});
                this.setState({ dialogOpen: false });
            })
            .catch(error => {
                //this.setState({error: error});
                console.error(error);
            });
    };

    render() {
        return <div>
            <VbAppBar>
                <Button size="small" href={"/categories"}>Categories</Button>
                <IconButton color={"inherit"} onClick={this.handleClickOpen} aria-label={"Add Box"}><CreateNewFolderIcon /></IconButton>
            </VbAppBar>
            <GridList cols={3} cellHeight={'auto'}>
            {
                this.state.items.map(function (value) { return <BoxComponent key={value.id} box={value} /> })
            }
            </GridList>
            <Dialog open={this.state.dialogOpen} onClose={this.handleClose} aria-labelledby="form-dialog-title">
                <DialogTitle id="form-dialog-title">New box</DialogTitle>
                <DialogContent>
                    <TextField autoFocus margin="dense" id="name" label="Name" type="text" onChange={this.setnewBoxName} fullWidth />
                </DialogContent>
                <DialogActions>
                    <Button onClick={this.handleCreate} color="primary">Create</Button>
                </DialogActions>
            </Dialog>
        </div>
    }
}

export default BoxList;