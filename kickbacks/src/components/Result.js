import React, { Component } from 'react';
class Result extends Component {
  state = {}
  render() { 
    // let startDate = new Date(this.props.data.start_date).toLocaleDateString();
    //     let endDate = new Date(this.props.data.end_date).toLocaleDateString();

        // let agencyName = this.props.data.agency_name
        // if (agencyName.length > 20) {
        //     agencyName = agencyName.slice(0, 17) + '...'
        // }

        // let sectionName = this.props.data.section_name
        // if (sectionName.length > 25) {
        //     sectionName = sectionName.slice(0, 22) + '...'
        // }

        // let shortTitle = this.props.data.short_title
        // if (shortTitle.length > 15) {
        //     shortTitle = shortTitle.slice(0, 12) + "...";
        // }

        // let additionalDesc = this.props.data.additional_description_1
        // if (additionalDesc) {
        //     if (additionalDesc.length > 30) {
        //       additionalDesc = additionalDesc.slice(0, 27) + "...";
        //     }
        // }
        console.log('this.props.data')
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
            </div>
      
   )
  }
}
 
export default Result;