import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWitsService } from 'app/shared/model/wits-service.model';

@Component({
    selector: 'jhi-wits-service-detail',
    templateUrl: './wits-service-detail.component.html'
})
export class WitsServiceDetailComponent implements OnInit {
    witsService: IWitsService;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ witsService }) => {
            this.witsService = witsService;
        });
    }

    previousState() {
        window.history.back();
    }
}
