import React, { Component } from 'react';
import './Result.css'
class Result extends Component {
  state = {}
  handleClick = () => {
    console.log(this.props.data);
    
    let data = this.props.data
    this.props.saveFav(data)
  }
  render() 
  { 

        // let data = this.props.data
        // console.log('adfadf', data)

        return (  
            // 
            <div onClick={this.handleClick} className="listitem">
                <p>{this.props.data.request_id}</p> 
                <p>{this.props.start_date}</p>
                <p>{this.props.endDate}</p>
                <p>{this.props.data.agency_name}</p>
                <p>{this.props.data.section_name}</p>
                <p>{this.props.data.short_title}</p>
                <p>{this.props.data.additional_description_1}</p>
                <button className="savebutton" onClick={this.props.handleClick}>SAVE TO TRACK</button>
            </div>
      
   )
  }
}
 
export default Result;