import React from 'react';
import {RouteComponentProps} from 'react-router'

import Category from "../Category/Category";

type Categories = {
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
        this.state = {items:[]};
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

    render() {
        return <div>
            {
                this.state.items.map(function (value) {
                    console.log(value);
                     return <Category key={value.groupId} groupId={value.groupId} groupName={value.groupName} boxCount={value.boxCount}/>
                })
            }
        </div>
    }
}

export default CategoryList;