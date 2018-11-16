/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RtoAppTestModule } from '../../../test.module';
import { WitsServiceDetailComponent } from 'app/entities/wits-service/wits-service-detail.component';
import { WitsService } from 'app/shared/model/wits-service.model';

describe('Component Tests', () => {
    describe('WitsService Management Detail Component', () => {
        let comp: WitsServiceDetailComponent;
        let fixture: ComponentFixture<WitsServiceDetailComponent>;
        const route = ({ data: of({ witsService: new WitsService(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtoAppTestModule],
                declarations: [WitsServiceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(WitsServiceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(WitsServiceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.witsService).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
