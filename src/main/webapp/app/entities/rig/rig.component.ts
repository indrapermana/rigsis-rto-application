import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRig } from 'app/shared/model/rig.model';
import { Principal } from 'app/core';
import { RigService } from './rig.service';

@Component({
    selector: 'jhi-rig',
    templateUrl: './rig.component.html'
})
export class RigComponent implements OnInit, OnDestroy {
    rigs: IRig[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private rigService: RigService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.rigService.query().subscribe(
            (res: HttpResponse<IRig[]>) => {
                this.rigs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRigs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRig) {
        return item.id;
    }

    registerChangeInRigs() {
        this.eventSubscriber = this.eventManager.subscribe('rigListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
