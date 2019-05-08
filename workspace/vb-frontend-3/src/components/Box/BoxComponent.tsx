import React from 'react';
import { Link } from 'react-router-dom'
import Flag from 'react-world-flags'

import Card from '@material-ui/core/Card';
import SvgIcon from '@material-ui/core/SvgIcon';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import LinearProgress from '@material-ui/core/LinearProgress';

import './Box.css';

const BoxComponent = ({box} ) => (
    <Card className={"card"}>
        <CardContent>
            <Typography className={"title"} variant="h5" component="h2">
                <SvgIcon>
                    <path d="M18.208,2.958H8.875V1.792c0-0.644-0.522-1.167-1.167-1.167H1.875c-0.644,0-1.167,0.522-1.167,1.167v16.333
                            c0,0.645,0.522,1.166,1.167,1.166h16.333c0.645,0,1.167-0.521,1.167-1.166v-14C19.375,3.48,18.853,2.958,18.208,2.958z
                             M18.208,17.542c0,0.322-0.261,0.583-0.583,0.583H2.458c-0.323,0-0.583-0.261-0.583-0.583V6.458h16.333V17.542z M17.625,5.292
                            H1.875V2.375c0-0.323,0.261-0.583,0.583-0.583h4.667c0.323,0,0.583,0.261,0.583,0.583v1.75h9.917c0.322,0,0.583,0.261,0.583,0.583
                            C18.208,5.031,17.947,5.292,17.625,5.292z" />
                </SvgIcon>
                <span>{box.name}</span>
                <Flag className={"flag"} code={ box.front } />
                <Flag className={"flag"} code={ box.back } />
            </Typography>
            <Typography>{box.verbCount} verbs</Typography>
            <br />
            <Typography>Progress Front</Typography>
            <LinearProgress variant="determinate" value={box.progressFront} />
            <br />
            <Typography>Progress Back</Typography>
            <LinearProgress variant="determinate" value={box.progressBack} />
        </CardContent>
        <CardActions>
            <Link to={"/box/" + box.id}>Open</Link>
        </CardActions>
    </Card>
);

export default BoxComponent;
