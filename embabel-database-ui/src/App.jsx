import { useState } from 'react'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css'

//pages
import Home from './components/pages/Home';
import Search from './components/pages/Search';
import Tags from './components/pages/Tags';
import Providers from './components/pages/Providers';
//components
import Menu from './components/layout/Menu';

function App() {

  return (
    <div className="bp6-dark">
      <Router>
        <div id="main-page">
          <div id="left-column">
            <Menu/>
          </div>
          <div id="content">
            <Routes>
              <Route exact path="/" element={<Home/>}/>
              <Route path="/search" element={<Search/>}/>
              <Route path="/tags" element={<Tags/>}/>
              <Route path="/providers" element={<Providers/>}/>
            </Routes>
          </div>
        </div>
      </Router>
    </div>
  )
}

export default App
