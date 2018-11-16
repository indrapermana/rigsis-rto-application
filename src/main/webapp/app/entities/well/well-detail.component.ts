import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWell } from 'app/shared/model/well.model';

@Component({
    selector: 'jhi-well-detail',
    templateUrl: './well-detail.component.html'
})
export class WellDetailComponent implements OnInit {
    well: IWell;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ well }) => {
            this.well = well;
        });
    }

    previousState() {
        window.history.back();
    }
}
