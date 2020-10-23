import { Component, OnInit } from '@angular/core';
import { SubblogService } from 'src/app/subblog/subblog.service';
import { SubblogModel } from 'src/app/subblog/subblog-response';


@Component({
  selector: 'app-subblog-side-bar',
  templateUrl: './subblog-side-bar.component.html',
  styleUrls: ['./subblog-side-bar.component.css']
})
export class SubblogSideBarComponent implements OnInit {
  subblogs: Array<SubblogModel> = [];
  displayViewAll: boolean;

  constructor(private subblogService: SubblogService) {
    this.subblogService.getAllSubblogs().subscribe(data => {
      if (data.length > 3) {
        this.subblogs = data.splice(0, 3);
        this.displayViewAll = true;
      } else {
        this.displayViewAll = true;
        this.subblogs = data;
      }
    });
  }

  ngOnInit(): void { }
}
