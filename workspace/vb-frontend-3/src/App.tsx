import React from 'react';
import { Router, Route, Switch} from 'react-router-dom';
import { createBrowserHistory } from 'history';

import Home from "./components/pages/Home/Home";
import Login from "./components/pages/Login/Login";
import CategoryList from "./components/pages/CategoryList/CategoryList";
import BoxList from "./components/pages/BoxList/BoxList";

import './App.css';


const history = createBrowserHistory();

const App: React.FC = () => {
    const PrivateRoute = ({ component: Component, ...rest }) => (
        <Route {...rest} render={(props) => (
            localStorage.getItem("authToken")
                ? <Component {...props} />
                : props.history.push("/login")
        )} />
    );

    return (
        <div>
            <Router path="" history={history}>
                <div>
                    <Switch>
                        <PrivateRoute exact path="/" component={Home} />
                        <PrivateRoute exact path="/categories" component={CategoryList} />
                        <PrivateRoute path="/category/:categoryId" component={BoxList} />
                        <PrivateRoute path="/box/:boxId" component={Home} />
                        <Route exact path="/login" component={Login} />
                        <Route render={() => <h1>Page not found</h1>} />
                    </Switch>
                </div>
            </Router>
        </div>
    );
};

export default App;
