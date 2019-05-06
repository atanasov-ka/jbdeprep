import React from 'react';
import {RouteComponentProps} from 'react-router'
import CardContent from "@material-ui/core/CardContent/CardContent";
import CardActions from "@material-ui/core/CardActions/CardActions";
import Button from "@material-ui/core/Button/Button";
import Card from "@material-ui/core/Card/Card";
import DialogActions from "@material-ui/core/DialogActions/DialogActions";
import TextField from "@material-ui/core/TextField/TextField";
import DialogContent from "@material-ui/core/DialogContent/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle/DialogTitle";
import Dialog from "@material-ui/core/Dialog/Dialog";

import Category from "../Category/Category";

type Categories = {
    dialogOpen: boolean,
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
        this.state = {dialogOpen:false, items:[]};
    }

    componentDidMount() {

        // this.state = { list:[{id:1, name: "a"}] };
        let url = 'http://localhost:8081/vb/api/box/byGroup';
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

    render() {
        return <div>
            {
                this.state.items.map(function (value) {
                    console.log(value);
                     return <Category key={value.groupId} groupId={value.groupId} groupName={value.groupName} boxCount={value.boxCount}/>
                })
            }
            <Card className={"card"}>
                <CardContent />
                <CardActions>
                    <Button size="small" onClick={this.handleClickOpen}>Add Category</Button>
                </CardActions>
            </Card>
            <Dialog open={this.state.dialogOpen} onClose={this.handleClose} aria-labelledby="form-dialog-title">
                <DialogTitle id="form-dialog-title">Create new Category</DialogTitle>
                <DialogContent>
                    <TextField autoFocus margin="dense" id="name" label="Category name" type="text" fullWidth />
                </DialogContent>
                <DialogActions>
                    <Button onClick={this.handleClose} color="primary">
                        Cancel
                    </Button>
                    <Button onClick={this.handleClose} color="primary">
                        Create
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    }
}

export default CategoryList;