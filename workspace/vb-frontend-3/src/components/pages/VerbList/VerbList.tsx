import React from 'react';
import {RouteComponentProps} from 'react-router'
import List from "@material-ui/core/List/List";
import ListItem from "@material-ui/core/ListItem/ListItem";

import VbAppBar from "../../elements/ButtonAppBar/VbAppBar";
import VerbItemComponent from "../../elements/Verb/Verb";
import { Verb } from "../BoxList/BoxList";

export type Verbs = {
    boxId: number,
    items: Array<Verb>
};

class VerbList extends React.Component<RouteComponentProps, Verbs> {
    constructor(props){
        super(props);

        this.componentDidMount = this.componentDidMount.bind(this);
        this.state = { boxId:0, items:[] };
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

    render() {
        return <div>
            <VbAppBar />
            <List>
            {
                this.state.items.map(function (value) { return <ListItem><VerbItemComponent key={value.id} verb={value} /></ListItem> })
            }
            </List>
        </div>
    }
}

export default VerbList;