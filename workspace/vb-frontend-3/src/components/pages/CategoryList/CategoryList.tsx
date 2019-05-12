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

import VbAppBar from "../../elements/VbAppBar/VbAppBar";
import Category from "../../elements/Category/Category";

import "../../elements/Category/Category.css";

type Categories = {
    dialogOpen: boolean,
    newCategoryName: string,
    items : Array<{
        "groupId": number,
        "groupName": string,
        "boxCount": number
    }>
};

class CategoryList extends React.Component<RouteComponentProps, Categories> {
    constructor(props){
        super(props);
        this.componentDidMount = this.componentDidMount.bind(this);
        this.state = {dialogOpen:false, newCategoryName:"", items:[]};
    }

    componentDidMount() {
        let url = 'http://localhost:8081/vb/api/category';
        fetch(url, { method: "GET", headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + localStorage.getItem("authToken")
            }})
            .then(response => response.text())
            .then(json => {
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

    setNewCategoryName = (event) => {
        this.setState({newCategoryName: event.target.value});
    };

    handleCreate = () => {
        let url = 'http://localhost:8081/vb/api/category';
        fetch(url, { method: "POST", headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Basic ' + localStorage.getItem("authToken")
            }, body: JSON.stringify({groupName:this.state.newCategoryName})})
            .then(response => response.text())
            .then(json => {
                console.log(JSON.parse(json));
                let newCategory = JSON.parse(json);
                let elems = this.state.items;
                elems.push(newCategory);
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
                <IconButton color={"inherit"} onClick={this.handleClickOpen} aria-label={"Add Category"}><CreateNewFolderIcon /></IconButton>
            </VbAppBar>
            <div>
                <GridList cols={3} cellHeight={'auto'}>
                    {
                    this.state.items.map(
                        function (value) {
                            return <Category key={value.groupId}
                                             groupId={value.groupId}
                                             groupName={value.groupName}
                                             boxCount={value.boxCount}/> })
                }
                </GridList>
            </div>
            <Dialog open={this.state.dialogOpen} onClose={this.handleClose} aria-labelledby="form-dialog-title">
                <DialogTitle id="form-dialog-title">New category</DialogTitle>
                <DialogContent>
                    <TextField autoFocus margin="dense" id="name" label="Name" type="text" onChange={this.setNewCategoryName} fullWidth />
                </DialogContent>
                <DialogActions>
                    <Button onClick={this.handleCreate} color="primary">Create</Button>
                </DialogActions>
            </Dialog>
        </div>
    }
}

export default CategoryList;