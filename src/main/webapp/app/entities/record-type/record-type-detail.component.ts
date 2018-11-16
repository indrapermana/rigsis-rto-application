import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRecordType } from 'app/shared/model/record-type.model';

@Component({
    selector: 'jhi-record-type-detail',
    templateUrl: './record-type-detail.component.html'
})
export class RecordTypeDetailComponent implements OnInit {
    recordType: IRecordType;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ recordType }) => {
            this.recordType = recordType;
        });
    }

    previousState() {
        window.history.back();
    }
}
