import React, { Component } from 'react';
import Favoritelist from './Favoritelist';
import Resultlist from './Resultlist';
import axios from 'axios';
class Home extends Component {
  state = {
    favorites: [],
    data: [],
    query: '',
    searchData: []
    }
    componentDidMount = async () => {
      await axios.get("https://data.cityofnewyork.us/resource/buex-bi6w.json").then((res) => {
        let data = [...res.data]
        this.setState({ data })
        console.log(data);
      })
      await axios.get("/favorites").then((res) => {
        let favorites = [...res.data]
        this.setState({ favorites})
        console.log(favorites);
      })
    }
    query = (query) => {
      this.setState({ query })
  
      let Data = this.state.data
      let searchData = []
  
      if (this.state.query !== "") {
        Data.forEach(data => {
          if (data.agency_name.includes(`${this.state.query}`) || data.request_id.includes(`${this.state.query}`) || data.section_name.includes(`${this.state.searchingFor}`) || data.short_title.includes(`${this.state.searchingFor}`)) {
            searchData.push(data);
          }
        })
        this.setState({ searchData })
      }
    }
    saveFav = async (data) => {
      console.log('save check ', data)
      console.log(Number(data.request_id));
      let newData = {
        ID: parseInt(data.request_id),
        title: data.agency_name + ', '+data.section_name,
        description: data.additional_description_1
      }
      await axios.post("/favorites", newData)
      console.log(this.state.favorites,newData)
      let favorites = [...this.state.favorites]
      favorites.push(newData)
      this.setState({ favorites })
      
    }
    deleteFav = async (id, index) => {
      
    
      console.log(id.id);
     
      await axios.delete(`/favorites/${id.id}`)
  
      let favorites = [...this.state.favorites]
      favorites.splice(index, 1)
      this.setState({ favorites})
    }
    
  render() { 
    return ( <div className="flexcontainer">
      {/* <nav className="navbar navbar-expand-lg navbar-dark navbar-custom fixed-top">
      <div className="container">
        <a className="navbar-brand" href="#">Start Bootstrap</a>
        <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarResponsive">
          <ul className="navbar-nav ml-auto">
            <li className="nav-item">
              <a className="nav-link" href="#">Sign Up</a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="#">Log In</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>

    <header className="masthead text-center text-white">
      <div className="masthead-content">
        <div className="container">
          <h1 className="masthead-heading mb-0">One Page Wonder</h1>
          <h2 className="masthead-subheading mb-0">Will Rock Your Socks Off</h2>
          <a href="#" className="btn btn-primary btn-xl rounded-pill mt-5">Learn More</a>
        </div>
      </div>
      <div className="bg-circle-1 bg-circle"></div>
      <div className="bg-circle-2 bg-circle"></div>
      <div className="bg-circle-3 bg-circle"></div>
      <div className="bg-circle-4 bg-circle"></div>
    </header>

    <section>
      <div className="container">
        <div className="row align-items-center">
          <div className="col-lg-6 order-lg-2">
            <div className="p-5">
              <img className="img-fluid rounded-circle" src="img/01.jpg" alt=""/>>            </div>
          </div>
          <div className="col-lg-6 order-lg-1">
            <div className="p-5">
              <h2 className="display-4">For those about to rock...</h2>
              <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quod aliquid, mollitia odio veniam sit iste esse assumenda amet aperiam exercitationem, ea animi blanditiis recusandae! Ratione voluptatum molestiae adipisci, beatae obcaecati.</p>
            </div>
          </div>
        </div>
      </div>
    </section>

    <section>
      <div className="container">
        <div className="row align-items-center">
          <div className="col-lg-6">
            <div className="p-5">
              <img className="img-fluid rounded-circle" src="img/02.jpg" alt=""/>
              </div>
          </div>
          <div className="col-lg-6">
            <div className="p-5">
              <h2 className="display-4">We salute you!</h2>
              <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quod aliquid, mollitia odio veniam sit iste esse assumenda amet aperiam exercitationem, ea animi blanditiis recusandae! Ratione voluptatum molestiae adipisci, beatae obcaecati.</p>
            </div>
          </div>
        </div>
      </div>
    </section>

    <section>
      <div className="container">
        <div className="row align-items-center">
          <div className="col-lg-6 order-lg-2">
            <div className="p-5">
              <img className="img-fluid rounded-circle" src="img/03.jpg" alt=""/>
            </div>
          </div>
          <div className="col-lg-6 order-lg-1">
            <div className="p-5">
              <h2 className="display-4">Let there be rock!</h2>
              <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quod aliquid, mollitia odio veniam sit iste esse assumenda amet aperiam exercitationem, ea animi blanditiis recusandae! Ratione voluptatum molestiae adipisci, beatae obcaecati.</p>
            </div>
          </div>
        </div>
      </div>
    </section>

   
    <footer className="py-5 bg-black">
      <div className="container">
        <p className="m-0 text-center text-white small">Copyright &copy; Your Website 2018</p>
      </div>
      
    </footer> */}
      <Resultlist
      data={this.state.data}
      query={this.state.query}
      searchData={this.state.searchData}
      saveFav={this.saveFav}
      />
      <Favoritelist 
      favorites={this.state.favorites}
      deleteFav={this.deleteFav}/>
    </div> )
  }
}
 
export default Home;