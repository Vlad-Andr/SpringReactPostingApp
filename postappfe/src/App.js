import './App.css';
import * as React from 'react';
import HeaderForm from "./components/forms/HeaderForm";
import FooterForm from "./components/forms/FooterForm";
import PostList from "./components/forms/PostList";

function App() {
    return (
        <div>
            <HeaderForm/>
            <PostList />
            <FooterForm/>
        </div>
    );
}

export default App;
