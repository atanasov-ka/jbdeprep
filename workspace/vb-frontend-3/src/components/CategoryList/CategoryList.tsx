import React from 'react';
import Category from "../Category/Category";

const greeting = {
    list:
        [
            {
                categoryName : "TEs11",
                boxId : 1
            },
            {
                categoryName : "Test2",
                boxId : 2
            },
            {
                categoryName : "Test3",
                boxId : 3
            }
        ]
};

const CategoryList = () => (
    <div>
    {
        greeting.list.map(function (value) {
            return <Category boxId={value.boxId} categoryName={value.categoryName}/>
        })
    }
    </div>
);

export default CategoryList;