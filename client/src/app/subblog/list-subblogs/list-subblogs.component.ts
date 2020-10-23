import { Component, OnInit } from '@angular/core';
import { SubblogModel } from '../subblog-response';
import { SubblogService } from '../subblog.service';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-list-subblogs',
  templateUrl: './list-subblogs.component.html',
  styleUrls: ['./list-subblogs.component.css']
})
export class ListSubblogsComponent implements OnInit {

  subblogs: Array<SubblogModel>;

  constructor(private subblogService: SubblogService) { }

  ngOnInit() {
    this.subblogService.getAllSubblogs().subscribe(data => {
      this.subblogs = data;
      console.log(this.subblogs);

    }, error => {
      throwError(error);
    })
  }
}
