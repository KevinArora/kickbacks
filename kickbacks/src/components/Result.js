import React, { Component } from 'react';
class Result extends Component {
  state = {}
  render() 
  { 

        let data = this.props.data
        // console.log('adfadf', data)

        return (  
            // onClick={this.handleClick}
            <div>
                <p>{this.props.data.request_id}</p> 
                <p>{this.props.start_date}</p>
                <p>{this.props.endDate}</p>
                <p>{this.props.data.agency_name}</p>
                <p>{this.props.data.section_name}</p>
                <p>{this.props.data.short_title}</p>
                <p>{this.props.data.additional_description_1}</p>
                <button onClick={this.props.saveFav}>save</button>
            </div>
      
   )
  }
}
 
export default Result;