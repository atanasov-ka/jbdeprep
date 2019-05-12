import React from 'react';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Button from "@material-ui/core/Button/Button";
import withStyles from "@material-ui/core/styles/withStyles";

const style = {
    appBar: {
        width: "auto"
    }
};

class VbAppBar extends React.Component {

    logout = () => {
        console.info("clearing local storage" + localStorage.getItem("authToken"));
        localStorage.clear();
        window.location.reload();
    };

    render() {
        return (
            <AppBar position="static" color="default" className={"appBar"}>
                <Toolbar>
                    <Typography variant="h6" color="inherit">
                        {/*{this.props.title}*/}
                    </Typography>
                    {this.props.children}
                    <Button style={{ marginLeft: "auto", marginRight: -12 }} onClick={this.logout}>Logout</Button>
                </Toolbar>
            </AppBar>
        );
    }
}

export default withStyles(style)(VbAppBar);