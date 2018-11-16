/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RtoAppTestModule } from '../../../test.module';
import { RigDetailComponent } from 'app/entities/rig/rig-detail.component';
import { Rig } from 'app/shared/model/rig.model';

describe('Component Tests', () => {
    describe('Rig Management Detail Component', () => {
        let comp: RigDetailComponent;
        let fixture: ComponentFixture<RigDetailComponent>;
        const route = ({ data: of({ rig: new Rig(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtoAppTestModule],
                declarations: [RigDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RigDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RigDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.rig).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
