import React from 'react';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Button from "@material-ui/core/Button/Button";

class VbAppBar extends React.Component {
    logout = () => {
        console.info("clearing local storage" + localStorage.getItem("authToken"));
        localStorage.clear();
        window.location.reload();
    };

    render() {
        return (
            <AppBar position="static" color="default">
                <Toolbar>
                    <Typography variant="h6" color="inherit">
                        {/*{this.props.title}*/}
                    </Typography>
                    <Button onClick={this.logout}>Logout</Button>
                    {this.props.children}
                </Toolbar>
            </AppBar>
        );
    }
}

export default VbAppBar;