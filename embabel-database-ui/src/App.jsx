import { useState } from 'react'
import { HashRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css'

//pages
import Home from './components/pages/Home';
import Search from './components/pages/Search';
import Tags from './components/pages/Tags';
import Providers from './components/pages/Providers';
import Maintenance from './components/pages/Maintenance';
import Recommender from './components/pages/Recommender';
//components
import MenuBlock from './components/layout/MenuBlock';

function App() {

  return (
    <div className="bp6-dark" style={{height: '100vh', width: '100vw', display: 'flex', flexDirection: 'column'}}>
      <Router>
        <div id="main-page">
          <div id="left-column">
            <MenuBlock/>
          </div>
          <div id="content">
            <Routes>
              <Route exact path="/" element={<Home/>}/>
              <Route path="/search" element={<Search/>}/>
              <Route path="/tags" element={<Tags/>}/>
              <Route path="/providers" element={<Providers/>}/>
              <Route path="/agent/maintenance" element={<Maintenance/>}/>
              <Route path="/agent/recommender" element={<Recommender/>}/>
            </Routes>
          </div>
        </div>
      </Router>
    </div>
  )
}

export default App
